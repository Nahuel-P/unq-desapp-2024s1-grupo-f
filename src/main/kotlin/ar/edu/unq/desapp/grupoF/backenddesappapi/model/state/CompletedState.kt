package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class CompletedState : TransactionState {
    override fun getState(): String {
        return "COMPLETED"
    }
}