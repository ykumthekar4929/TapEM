# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from Users.models import UserModel

# Create your models here.

class EventModel(models.Model):
        event = models.AutoField( primary_key=True)
        title = models.TextField( null=True, blank=True, default=None)
        description = models.TextField( null=True, blank=True, default=None)
        from_t_stamp = models.DateTimeField(default=None)
        to_t_stamp = models.DateTimeField(default=None)
        created_by_user = models.ForeignKey(UserModel, related_name='created_by_user',on_delete=models.CASCADE)
        caterer = models.ForeignKey(UserModel, related_name='caterer_assigned',on_delete=models.CASCADE)
        location = models.TextField( null=True, blank=True, default=None)
        status = models.TextField( null=True, blank=True, default=None)
        meals =  models.TextField( null=True, blank=True, default=None)
        meal_type =  models.TextField( null=True, blank=True, default=None)
        meal_formality =  models.TextField( null=True, blank=True, default=None)
        serving_alcohol = models.BooleanField(default=False)
        cost = models.IntegerField(blank=True, null=True)

        class Meta:
                db_table = 'events'

        def __unicode__(self):
                return self.title

# class EventStaffModel(models.Model):
#         map = models.AutoField( primary_key=True)
#         staff = models.ForeignKey(UserModel, related_name='staff_assigned',on_delete=models.CASCADE)
#         event = models.ForeignKey(EventModel, related_name='related_event',on_delete=models.CASCADE)
#
#         class Meta:
#                 db_table = 'event_staff_map'
#
#         def __unicode__(self):
#                 return self.map
