package ar.edu.unq.desapp.grupoF.backenddesappapi.service.impl

import ar.edu.unq.desapp.grupoF.backenddesappapi.model.PriceHistory
import ar.edu.unq.desapp.grupoF.backenddesappapi.model.enums.CryptoSymbol
import ar.edu.unq.desapp.grupoF.backenddesappapi.repositories.PriceHistoryRepository
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.ICommonService
import ar.edu.unq.desapp.grupoF.backenddesappapi.service.IPriceHistoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PriceHistoryServiceImpl @Autowired constructor(
    private val priceHistoryRepository: PriceHistoryRepository,
    private val commonService: ICommonService,
) : IPriceHistoryService {

    override fun getLast24hsQuotes(symbol: CryptoSymbol): List<PriceHistory> {
        val cryptocurrency = commonService.getCrypto(symbol)
        val prices = priceHistoryRepository.findByCryptocurrency(symbol)
        return cryptocurrency.getLast24hsQuotes(prices)
    }
}