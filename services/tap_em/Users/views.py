# -*- coding: utf-8 -*-
from __future__ import unicode_literals
from rest_framework.response import Response
from rest_framework import viewsets
from models import UserModel
from django_filters.rest_framework import FilterSet
from django.shortcuts import render
from serializers import UsersSerializer
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import status
import psycopg2
import tap_em.settings as tap_em_settings
from psycopg2.extras import RealDictCursor

default_database = tap_em_settings.DATABASES.get('default')
conn = psycopg2.connect(database=default_database.get('NAME'), user=default_database.get('USER'),
                        password=default_database.get('PASSWORD'), host=default_database.get('HOST'),
                        port=default_database.get('PORT'), cursor_factory=RealDictCursor)
cur = conn.cursor()


class UsersFilter(FilterSet):
    class Meta:
        model = UserModel
        fields = {
                "first_name": ['gt', 'lt', 'gte', 'lte', 'exact'],
                "last_name": ['gt', 'lt', 'gte', 'lte', 'exact'],
                "password": ['exact'],
                "mav_id": ['exact'],
                "status": ['exact'],
                "role": ['exact'],
        }

class UsersViewset(viewsets.ModelViewSet):
        filter_class = UsersFilter
        queryset = UserModel.objects.filter()
        serializer_class = UsersSerializer
        filter_backends = (DjangoFilterBackend,)



class DeleteUser(viewsets.ModelViewSet):
        filter_class = UsersFilter
        queryset = UserModel.objects.filter()
        serializer_class = UsersSerializer
        filter_backends = (DjangoFilterBackend,)

        def create(self, request):
                user_id = self.request.data.get("user_id")
                UserModel.objects.get(user_id=user_id).delete()
                return Response({"status":"DELETED"}, status=status.HTTP_201_CREATED)



class UserLogin(viewsets.ModelViewSet):
        filter_class = UsersFilter
        queryset = UserModel.objects.filter()
        serializer_class = UsersSerializer
        filter_backends = (DjangoFilterBackend,)
        def create(self, request):
                error = ""
                user = ""
                user_mav_id = request.data.get('mav_id')
                password = request.data.get('password')
                user_item_qset = UserModel.objects.filter(mav_id=user_mav_id)
                if user_item_qset:
                        current_user = user_item_qset[0]
                        if current_user.password == password:
                                user = current_user
                                if current_user.status != "ACTIVE":
                                        error = "Not Authorized to login"
                        else:
                                error = "Wrong password"
                else:
                        error = "No User Found"
                if len(error) > 1:
                        return Response({"error":error}, status=status.HTTP_201_CREATED)
                        pass
                else:
                        return Response(self.serializer_class(user).data, status=status.HTTP_201_CREATED)

class RegisterUser(viewsets.ModelViewSet):
        filter_class = UsersFilter
        queryset = UserModel.objects.filter()
        serializer_class = UsersSerializer
        filter_backends = (DjangoFilterBackend,)

        def update(self, request, *args, **kwargs):
                user = self.request.data
                item_to_update, item_to_create = UserModel.objects.update_or_create(
                        user_id = user.get('user_id'),
                        mav_id=user.get("mav_id"),
                        defaults={
                                                "first_name":user.get("first_name"),
                                                "last_name":user.get("last_name"),
                                                "role":user.get("role"),
                                                "sex":user.get("sex"),
                                                "birth_date":user.get("birth_date"),
                                                "password":user.get("password"),
                                                "email_id":user.get("email_id"),
                                                "status":user.get("status"),
                                        }
                )
                return Response(self.serializer_class(item_to_update).data, status=status.HTTP_201_CREATED)

        def create(self, request):
                mav_id = request.data.get('mav_id')
                first_name = request.data.get('first_name')
                last_name = request.data.get('last_name')
                role = request.data.get('role')
                sex = request.data.get('sex')
                birth_date = request.data.get('birth_date')
                password = request.data.get('password')
                email_id = request.data.get('email_id')
                object = {
                    "mav_id": mav_id,
                    "first_name": first_name,
                    "last_name": last_name,
                    "role": role,
                    "sex": sex,
                    "birth_date": birth_date,
                    "password": password,
                    "email_id": email_id,
                    "status": "REQUESTED"
                }
                serializer = UsersSerializer(data=object)
                if serializer.is_valid():
                        serializer.save()
                        return Response({"status":"done"}, status=status.HTTP_201_CREATED)
                else:
                        return Response(serializer.errors, status=status.HTTP_201_CREATED)
