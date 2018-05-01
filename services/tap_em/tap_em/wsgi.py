"""
WSGI config for tap_em project.

It exposes the WSGI callable as a module-level variable named ``application``.

For more information on this file, see
https://docs.djangoproject.com/en/1.11/howto/deployment/wsgi/
"""

# import os
#
# from django.core.wsgi import get_wsgi_application
# os.environ.setdefault("DJANGO_SETTINGS_MODULE", "tap_em.settings")
#
# application = get_wsgi_application()


import os
import sys
import django
from django.core.handlers.wsgi import WSGIHandler


os.environ.setdefault("DJANGO_SETTINGS_MODULE", "eisentask.settings.production")
django.setup(set_prefix=False)


DJANGO_PATH =  os.path.join(os.path.abspath(os.path.dirname(__file__)), '..')
sys.path.append(DJANGO_PATH)



application = WSGIHandler()
