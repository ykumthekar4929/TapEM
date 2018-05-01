from rest_framework import serializers
from .models import EventModel

class EventSerializer(serializers.ModelSerializer):
    class Meta:
        '''
        Override keys in fields to return specific keys

        '''
        model = EventModel
        fields = '__all__'



class EventDataSerializer(serializers.ModelSerializer):
        caterer_name = serializers.CharField(default=None)
        caterer_email = serializers.EmailField(default=None)
        class Meta:
                model = EventModel
                fields = '__all__'
