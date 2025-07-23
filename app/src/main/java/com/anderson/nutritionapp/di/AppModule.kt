package com.anderson.nutritionapp.di

import android.app.Application
import androidx.room.Room
import com.anderson.nutritionapp.data.local.FoodDao
import com.anderson.nutritionapp.data.local.FoodTypeConverters
import com.anderson.nutritionapp.data.local.NutritionDatabase
import com.anderson.nutritionapp.data.manager.LocalUserManagerImpl
import com.anderson.nutritionapp.data.remote.NutritionApi
import com.anderson.nutritionapp.data.repository.NutritionRepositoryImpl
import com.anderson.nutritionapp.domain.manager.LocalUserManager
import com.anderson.nutritionapp.domain.repository.NutritionRepository
import com.anderson.nutritionapp.domain.usecase.app_entry.AppEntryUseCases
import com.anderson.nutritionapp.domain.usecase.app_entry.ReadAppEntry
import com.anderson.nutritionapp.domain.usecase.app_entry.SaveAppEntry
import com.anderson.nutritionapp.domain.usecase.nutrition.GetFoodById
import com.anderson.nutritionapp.domain.usecase.nutrition.GetFoodCategories
import com.anderson.nutritionapp.domain.usecase.nutrition.GetRecipeTypes
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import com.anderson.nutritionapp.domain.usecase.nutrition.SearchFoods
import com.anderson.nutritionapp.domain.usecase.nutrition.SearchRecipes
import com.anderson.nutritionapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)


    @Provides
    @Singleton
    fun provideAppEntryUseCase(localUserManager: LocalUserManager) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager), saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideNutritionApi(): NutritionApi {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionApi::class.java)
    }
    @Provides
    @Singleton
    fun providesNutritionRepository(
        nutritionApi: NutritionApi
    ): NutritionRepository = NutritionRepositoryImpl(nutritionApi)

    @Provides
    @Singleton
    fun provideNutritionUseCases(
        nutritionRepository: NutritionRepository
    ): NutritionUseCases {
        return NutritionUseCases(
            getFoodCategories = GetFoodCategories(nutritionRepository),
            searchFoods = SearchFoods(nutritionRepository),
            getFoodById = GetFoodById(nutritionRepository),
            getRecipeTypes = GetRecipeTypes(nutritionRepository),
            searchRecipes = SearchRecipes(nutritionRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNutritionDatabase(
        application: Application
    ): NutritionDatabase{
        return Room.databaseBuilder(
            context = application,
            klass = NutritionDatabase::class.java,
            name = "nutrition_database"
        ).addTypeConverter(FoodTypeConverters()).build()
    }

    @Provides
    @Singleton
    fun provideNutritionDao(
        nutritionDatabase: NutritionDatabase
    ): FoodDao = nutritionDatabase.foodDao

}