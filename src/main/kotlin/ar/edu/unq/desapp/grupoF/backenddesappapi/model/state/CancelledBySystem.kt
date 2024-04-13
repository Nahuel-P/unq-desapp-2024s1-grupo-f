package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class CancelledBySystem : TransactionState {
    override fun getState(): String {
        return "CANCELLEDBYSYSTEM"
    }
}