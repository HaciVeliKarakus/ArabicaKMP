package di

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import screens.shop.ShopScreenModel
import screens.branch.BranchScreenModel
import screens.course.CourseScreenModel
import screens.product.ProductScreenModel

fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(commonModule())
}

fun commonModule() = module {
    factoryOf(::ProductScreenModel)
    factoryOf(::BranchScreenModel)
    factoryOf(::CourseScreenModel)
    factoryOf(::ShopScreenModel)
}
