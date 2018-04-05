# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from Users.models import UserModel

# Create your models here.

class EventModel(models.Model):

    event = models.IntegerField(max_length=100, primary_key=True)
    title = models.TextField( null=True, blank=True, default=None)
    description = models.TextField( null=True, blank=True, default=None)
    data_time = models.DateTimeField()
    created_by_user = models.ForeignKey(UserModel, related_name='created_by_user',on_delete=models.CASCADE)
    caterer = models.ForeignKey(UserModel, related_name='caterer_assigned',on_delete=models.CASCADE)

    class Meta:
        db_table = 'events'

    def __unicode__(self):
        return self.title
