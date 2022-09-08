import SwiftUI
import SafeHelloSDK

struct ContentView: View {
    @State private var eventId: String = ""
    @State private var config: Configuration?
    private let hostUserId = "host-id"
    private let participantId = "participant-id"
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                Button(action: createNewSafeHelloSession) {
                    Text("Create new SafeHello session")
                        .foregroundColor(Color.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.init(uiColor: UIColor.systemBlue))
                        .clipShape(Capsule())
                }

                HStack {
                    VStack {
                        Divider()
                    }

                    Text("OR")

                    VStack {
                        Divider()
                    }
                }
                .font(.subheadline.bold())
                .padding(.vertical)
                
                TextField("Enter the Event Id", text: $eventId)
                    .padding(.horizontal)
                    .frame(height: 50)
                    .textFieldStyle(.plain)
                    .border(Color.gray, width: 1.0)
                    

                Button(action: connectToExistingSafeHelloSession) {
                    Text("Connect to existing SafeHello session")
                        .foregroundColor(Color.white)
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.init(uiColor: UIColor.systemBlue))
                        .clipShape(Capsule())
                }
            }
            .padding(.horizontal)
            .font(.callout.bold())
            .showSafeHello(configuration: $config)
        }
        .preferredColorScheme(.light)
    }
    
    private func connectToSafeHello(with userId: String, completion: @escaping (Error?) -> Void) {
        let url = URL(string: "http://127.0.0.1:80/tokens/\(userId)")!
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard let data = data, let token = try? JSONDecoder().decode([String: String].self, from: data)["token"] else {
                print(">>> An error has ocurred while fetching token")
                return completion(error)
            }

            DispatchQueue.main.async {
                SafeHelloClient.shared.token = token
                SafeHelloClient.shared.connect { error in
                    if let error = error {
                        print(">>> SafeHello connection failed with error: \(error)")
                    } else {
                        print(">>> SafeHello connected successfully!")
                    }
                    completion(error)
                }
            }
        }.resume()
    }
    
    private func makeConfig(userId: String, eventId: String) -> Configuration {
        Configuration(
            userId: userId,
            eventId: eventId,
            title: "Demo event",
            subtitle: "8:00PM"
        )
    }
    
    private func createNewSafeHelloSession() {
        connectToSafeHello(with: hostUserId) { error in
            guard error == nil else {
                return
            }
            
            SafeHelloClient.shared.createEvent(
                senderId: hostUserId,
                receiverId: participantId,
                completion: { result in
                    do {
                        let event = try result.get()
                        print(">>> Event Id: \(event.id)")
                        self.config = makeConfig(
                            userId: hostUserId,
                            eventId: event.id
                        )
                    } catch {
                        print(">>> An error has ocurred while creating event")
                    }
                }
            )
        }
    }
    
    private func connectToExistingSafeHelloSession() {
        connectToSafeHello(with: participantId) { error in
            guard error == nil else { return }
            config = makeConfig(userId: participantId, eventId: eventId)
        }
    }

}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .preferredColorScheme(.light)
    }
}
