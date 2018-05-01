from django.conf.urls import url
from rest_framework import routers
from views import EventHandler
from views import EventData
# from views import EventStaffMapping
router = routers.DefaultRouter()
router.register(r'events_data', EventHandler)
router.register(r'get_event_data',EventData)
# router.register(r'get_events_assigned',EventStaffMapping)
urlpatterns = router.urls
