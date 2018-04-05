# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.
class UserModel(models.Model):

    user = models.IntegerField(max_length=100, primary_key=True)
    mav_id = models.TextField()
    first_name = models.TextField( null=True, blank=True, default=None)
    last_name = models.TextField( null=True, blank=True, default=None)
    role = models.TextField(  null=True)
    sex = models.TextField(  null=True)
    birth_date = models.DateTimeField()
    password = models.TextField()
    email_id = models.TextField()

    class Meta:
        db_table = 'users'

    def __unicode__(self):
        return self.first_name
