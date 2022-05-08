import praw
import requests
import sys
 
subr = 'u_DemoAccountAPI'
 
reddit = praw.Reddit(client_id="K3KnohvllUSMa016r3XRLg",
                     client_secret="dqu86Gy_4NJ71yJszUmf0GCcPnp_Vg",
                     user_agent="script by u/DemoAccountAPI",
                     redirect_uri="https://www.example.com/",
                     refresh_token="1604508378810-RagYKzVH_LWdloKSSX9FAWpzK7nuHQ")
 
subreddit = reddit.subreddit(subr)

description = sys.argv[1]
title = " ".join(sys.argv[2:])


subreddit.submit(title,selftext=description)