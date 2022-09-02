import Vapor

fileprivate let token = Environment.get("TOKEN")!

func routes(_ app: Application) throws {
    app.get("tokens", ":userId") { req async throws -> TokenResponse in
        return try await req.client.post(app.environment.safeHelloTokensEndpoint) { clientReq in
            clientReq.headers.add(name: "Authorization", value: token)
            try clientReq.content.encode(["userId": req.parameters.get("userId")!])
        }.content.decode(TokenResponse.self)
    }
}

fileprivate struct TokenResponse: Content {
    let token: String
}

fileprivate extension Environment {
    var safeHelloTokensEndpoint: URI {
        switch self {
        case .development:
            return "https://api.dev.safehello.com/v1/auth/tokens"
        case .testing:
            return "https://api.qa.safehello.com/v1/auth/tokens"
        case .production:
            fallthrough
        default:
            return "https://api.prod.safehello.com/v1/auth/tokens"
        }
    }
}
