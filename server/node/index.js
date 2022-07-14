const { SafeHello } = require('@safehello/sdk-typescript')
const express = require('express')
const app = express()
const port = 3000
const apiToken = '<<API_TOKEN>>'

/**
 * With the apiToken, you can call all the methods of the SDK.
 */
const apiClient = new SafeHello(apiToken)
const senderId = 'test-safehello-user'

app.get('/', async (req, res) => {
  const userToken = await apiClient.getAuthToken(senderId)
  /*
  With the userToken, you can call the createEvent and getEvent methods of the SDK.
  */
  //const userClient = new SafeHello(userToken)
  res.send({ token: userToken })
})

app.get('/create', async (req, res) => {
  const event = await apiClient.createEvent({ senderId })
  res.send(event)
})

app.get('/get', async (req, res) => {
  // ie: http://localhost:3000/get?eventId=01G7YRQQ45B8GS5JDTPZ1RK401
  const eventId = req.query.eventId
  if (eventId) {
    const event = await apiClient.getEvent(eventId)
    res.send(event)
  }
  res.error('No event created')
})

app.listen(port, () => {
  console.log(`SafeHello app listening on port ${port}`)
})
