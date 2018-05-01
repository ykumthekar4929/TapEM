# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from rest_framework.response import Response
from rest_framework import viewsets
from models import EventModel
from django_filters.rest_framework import FilterSet
from django.shortcuts import render
from serializers import EventSerializer
from serializers import EventDataSerializer
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework import status
import psycopg2
import tap_em.settings as tap_em_settings
from psycopg2.extras import RealDictCursor
from Users.models import UserModel
import random
import json
from django.forms.models import model_to_dict
# from models import EventStaffModel

import psycopg2
import tap_em.settings as tap_em_settings
from psycopg2.extras import RealDictCursor

default_database = tap_em_settings.DATABASES.get('default')


class EventsFilter(FilterSet):
    class Meta:
        model = EventModel
        fields = {
                "status": ['exact'],
                "event": ['exact'],
                "created_by_user": ['exact'],

        }

# class EventStaffFilter(FilterSet):
#         class Meta:
#             model = EventModel
#             fields = {
#                         "event":['exact'],
#                         # "staff":['exact']
#             }
# Create your views here.


class EventData(viewsets.ModelViewSet):
        filter_class = EventsFilter
        queryset = EventModel.objects.all()
        serializer_class = EventDataSerializer
        filter_backends = (DjangoFilterBackend,)

        def list(self, request):
                event_id = self.request.query_params.get("event_id")
                events_q_set = EventModel.objects.get(event=event_id)
                caterer_name = "%s %s"%(events_q_set.caterer.first_name, events_q_set.caterer.last_name)
                caterer_email = events_q_set.caterer.email_id
                events_q_set = model_to_dict(events_q_set)
                events_q_set.update({
                        'caterer_name':caterer_name,
                        'caterer_email':caterer_email
                })
                eventSerializer = EventDataSerializer(data = events_q_set)
                return Response(eventSerializer.initial_data,  status=status.HTTP_201_CREATED)

# class EventStaffMapping(viewsets.ModelViewSet):
#         filter_class = EventStaffFilter
#         queryset = EventStaffModel.objects.all()
#         serializer_class = EventSerializer
#         filter_backends = (DjangoFilterBackend)
#
#
#         def list(self, request):
#                 staff_id = self.request.query_params.get("staff_id")
#                 events_assigned_map = EventStaffModel.objects.filter(staff=staff_id)
#                 event_assigned = [item.event for item in events_assigned_map]
#                 serializer = EventSerializer(data=event_assigned, many=True)
#                 return Response(serializer.initial_data, status=status.HTTP_201_CREATED)
#

class EventHandler(viewsets.ModelViewSet):
        filter_class = EventsFilter
        queryset = EventModel.objects.all()
        serializer_class = EventSerializer
        filter_backends = (DjangoFilterBackend,)

        def update(self, request, *args, **kwargs):
                # event = self.request.data
                # item_to_update, item_to_create = EventModel.objects.update_or_create(
                #         event = event.get('event_id'),
                #         defaults={
                #                                 "status":event.get("status"),
                #                         }
                # )
                conn = psycopg2.connect(database=default_database.get('NAME'), user=default_database.get('USER'),
                                        password=default_database.get('PASSWORD'), host=default_database.get('HOST'),
                                        port=default_database.get('PORT'), cursor_factory=RealDictCursor)
                cur = conn.cursor()
                update_query = "UPDATE events SET status = \'%s\' WHERE (event=%s);"%(self.request.data.get('status'), self.request.data.get('event_id'))
                cur.execute(update_query)
                conn.commit()
                cur.close()
                conn.close()
                return Response({"status":"DONe"}, status=status.HTTP_201_CREATED)


        def get_random_caterer(self):
                users_q_set = UserModel.objects.filter(role="CATERER")
                return random.choice(users_q_set)

        def create(self, request):
                rand_caterer = self.get_random_caterer()
                request.data.update({
                                "caterer" : rand_caterer.user_id
                })
                serializer = self.serializer_class(data=request.data)
                if serializer.is_valid():
                        serializer.save()
                        return Response(serializer.data, status=status.HTTP_201_CREATED)
                else:
                        return Response(serializer.errors, status=status.HTTP_201_CREATED)
