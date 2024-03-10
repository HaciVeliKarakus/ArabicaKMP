package di

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import screens.about.AboutScreenModel
import screens.branch.BranchScreenModel
import screens.product.ProductScreenModel

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(commonModule())
}

fun commonModule() = module {
    factoryOf(::AboutScreenModel)
    factoryOf(::ProductScreenModel)
    factoryOf(::BranchScreenModel)
}
