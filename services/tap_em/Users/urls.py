from django.conf.urls import url
from rest_framework import routers
from views import UserLogin
from views import RegisterUser
from views import UsersViewset
from views import DeleteUser

router = routers.DefaultRouter()
router.register(r'get_users', UsersViewset)
router.register(r'user_login', UserLogin)
router.register(r'register', RegisterUser)
router.register(r'delete', DeleteUser)

urlpatterns = router.urls
