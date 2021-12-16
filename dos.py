import concurrent
import requests

def request_post(url, data):
    return requests.post(url, data=data)

with concurrent.futures.ThreadPoolExecutor() as executor: # optimally defined number of threads
    res = [executor.submit(request_post, url, data) for data in names]
    concurrent.futures.wait(res)