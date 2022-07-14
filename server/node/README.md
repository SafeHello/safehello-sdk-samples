#Init
Add your api token to the Dockerfile.

```bash
docker build . -t safehello-server
docker run -p 80:8080 safehello-server
```

You may then access the services from your browser:
http://localhost/tokens/sample-user-id
http://localhost/create/sample-user-id
http://localhost/get/<EVENT_ID>

For complete documentation of the SDK, see here:
https://github.com/SafeHello/safehello-sdk-samples/wiki/6.4-Rest-API-Overview
