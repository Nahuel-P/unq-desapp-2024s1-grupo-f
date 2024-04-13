package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class CancelledByUserState : TransactionState {
    override fun getState(): String {
        return "CANCELLEDBYUSER"
    }
}