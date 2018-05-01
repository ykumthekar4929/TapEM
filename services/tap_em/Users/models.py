# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.
class UserModel(models.Model):

    user_id = models.AutoField(primary_key=True, default=None)
    mav_id = models.TextField(unique=True, default=None)
    first_name = models.TextField( null=True, blank=True, default=None)
    last_name = models.TextField( null=True, blank=True, default=None)
    role = models.TextField(  null=True)
    sex = models.TextField(  null=True)
    birth_date = models.DateTimeField()
    password = models.TextField()
    email_id = models.TextField()
    status = models.TextField()

    class Meta:
        db_table = 'users'

    def __unicode__(self):
        return self.first_name
