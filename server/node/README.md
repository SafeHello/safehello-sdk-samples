#Init
Add your api token to the Dockerfile.

```bash
docker build . -t safehello-server
docker run -p 3000:3000 safehello-server
```

You may then access the services from your browser:
http://localhost:3000/tokens/sample-user-id
http://localhost:3000/create/sample-user-id
http://localhost:3000/get/<EVENT_ID>

For complete documentation of the SDK, see here:
https://github.com/SafeHello/safehello-sdk-samples/wiki/6.4-Rest-API-Overview
