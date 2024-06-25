package com.finance.android.walletwise
import android.app.Application
import com.finance.android.walletwise.model.Transaction.AppContainer
import com.finance.android.walletwise.model.Transaction.AppDataContainer
import com.finance.android.walletwise.model.Transaction.AppDatabase

class WalletWiseApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
        container = AppDataContainer(this)
    }

}