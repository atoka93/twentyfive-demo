package com.attilaszabo.twentyfivedemo.di

import android.content.Context
import com.attilaszabo.twentyfivedemo.features.cutofftime.data.CutOffTimeRepositoryImpl
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.CutOffTimeRepository
import com.attilaszabo.twentyfivedemo.features.cutofftime.domain.GetCutOffTimeStateListUseCase
import com.attilaszabo.twentyfivedemo.features.cutofftime.presentation.CustomClock
import com.attilaszabo.twentyfivedemo.features.cutofftime.presentation.CutOffTimeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.time.OffsetDateTime

fun cutOffTimeModule(context: Context): Module =
    module {
        viewModel<CutOffTimeViewModel> {
            CutOffTimeViewModel(
                getCutOffTimeStateListUseCase = get(),
                clock = get(),
                backgroundDispatcher = get(qualifier = CPU_DISPATCHER_QUALIFIER),
            )
        }
        scope<CutOffTimeViewModel> {
            scopedOf(::GetCutOffTimeStateListUseCase)
        }

        single<CustomClock> {
            CustomClock { OffsetDateTime.now() }
        }

        single<CutOffTimeRepository> {
            CutOffTimeRepositoryImpl(
                cutOffTimeDataSource = get(),
                countryDataSource = get(),
                dispatcher = get(qualifier = IO_DISPATCHER_QUALIFIER),
            )
        }
    }
