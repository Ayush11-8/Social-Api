import praw
import requests
 
subr = 'u_DemoAccountAPI'
 
reddit = praw.Reddit(client_id="K3KnohvllUSMa016r3XRLg",
                     client_secret="dqu86Gy_4NJ71yJszUmf0GCcPnp_Vg",
                     user_agent="script by u/DemoAccountAPI",
                     redirect_uri="https://www.example.com/",
                     refresh_token="1604508378810-RagYKzVH_LWdloKSSX9FAWpzK7nuHQ")
 
subreddit = reddit.subreddit(subr)
 
title = 'Just Made My Fifth Post Using Python.'
selftext = '''
testing from spring boot controller runtime exec

https://www.youtube.in
'''
 
subreddit.submit(title,selftext=selftext)