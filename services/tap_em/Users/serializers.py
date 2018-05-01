from rest_framework import serializers
from .models import UserModel


class UsersSerializer(serializers.ModelSerializer):
    class Meta:
        '''
        Override keys in fields to return specific keys

        '''
        model = UserModel
        fields = '__all__'
