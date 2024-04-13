package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class PaidState : TransactionState {
    override fun getState(): String {
        return "PAID"
    }
}