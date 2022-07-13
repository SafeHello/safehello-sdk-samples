import Vapor

fileprivate let token = Environment.get("TOKEN")!
fileprivate let safeHelloTokensEndpoint: URI = "https://api.prod.safehello.com/auth/tokens"

func routes(_ app: Application) throws {
    app.get("tokens", ":userId") { req async throws -> TokenResponse in
        return try await req.client.post(safeHelloTokensEndpoint) { clientReq in
            clientReq.headers.add(name: "x-api-key", value: token)
            try clientReq.content.encode(["userId": req.parameters.get("userId")!])
        }.content.decode(TokenResponse.self)
    }
}

fileprivate struct TokenResponse: Content {
    let token: String
}
