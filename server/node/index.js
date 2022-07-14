const { SafeHello } = require('@safehello/sdk-typescript')
const express = require('express')
const app = express()
const port = 3000
const apiToken = process.env.API_TOKEN

/**
 * With the apiToken, you can call all the methods of the SDK.
 */
const apiClient = new SafeHello(apiToken)

app.get('/tokens/:userId', async (req, res) => {
  // ie: http://localhost:3000/tokens/sample-user-id
  const { userId } = req.params
  const userToken = await apiClient.getAuthToken(userId)
  /*
  With the userToken, you can call the createEvent and getEvent methods of the SDK.
  */
  //const userClient = new SafeHello(userToken)
  res.send({ token: userToken })
})

app.get('/create/:userId', async (req, res) => {
  // ie: http://localhost:3000/create/sample-user-id
  const { userId } = req.params
  const event = await apiClient.createEvent({ senderId: userId })
  res.send(event)
})

app.get('/get/:eventId', async (req, res) => {
  // ie: http://localhost:3000/get/01G7YRQQ45B8GS5JDTPZ1RK401
  const { eventId } = req.params.eventId
  const event = await apiClient.getEvent(eventId)
  res.send(event)
})

app.listen(port, () => {
  console.log(`SafeHello app listening on port ${port}`)
})
