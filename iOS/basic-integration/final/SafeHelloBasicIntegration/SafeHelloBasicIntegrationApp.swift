import SwiftUI
import CoreLocation

@main
struct SafeHelloBasicIntegrationApp: App {
    private let location = CLLocationManager()
    @Environment(\.scenePhase) var scenePhase
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .onChange(of: scenePhase) { newValue in
                    switch newValue {
                    case .active:
                        location.requestWhenInUseAuthorization()
                    default:
                        break
                    }
                }
        }
    }
}
