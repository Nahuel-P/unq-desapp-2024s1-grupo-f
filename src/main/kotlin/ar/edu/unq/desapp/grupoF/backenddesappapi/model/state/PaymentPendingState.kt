package ar.edu.unq.desapp.grupoF.backenddesappapi.model.state

class PaymentPendingState : TransactionState {
    override fun getState(): String {
        return "PAYMENT_PENDING"
    }
}