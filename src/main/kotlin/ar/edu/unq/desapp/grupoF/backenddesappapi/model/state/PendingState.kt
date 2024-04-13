package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class PendingState : TransactionState {
    override fun getState(): String {
        return "PENDING"
    }
}