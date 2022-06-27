import SwiftUI

struct ContentView: View {
    @State private var eventId: String = ""
    
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
        }
        .preferredColorScheme(.light)
    }
    
    private func createNewSafeHelloSession() {
        
    }
    
    private func connectToExistingSafeHelloSession() {
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
            .preferredColorScheme(.light)
    }
}
