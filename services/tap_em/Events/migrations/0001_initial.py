# Generated by Django 2.0.4 on 2018-04-05 23:35

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('Users', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='EventModel',
            fields=[
                ('event', models.IntegerField(max_length=100, primary_key=True, serialize=False)),
                ('title', models.TextField(blank=True, default=None, null=True)),
                ('description', models.TextField(blank=True, default=None, null=True)),
                ('data_time', models.DateTimeField()),
                ('caterer', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='caterer_assigned', to='Users.UserModel')),
                ('created_by_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='created_by_user', to='Users.UserModel')),
            ],
            options={
                'db_table': 'events',
            },
        ),
    ]
