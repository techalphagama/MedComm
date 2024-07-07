package com.alpharays.mymedicommfma.communityv2.community_app.di


import com.alpharays.mymedicommfma.communityv2.CommunityFeatureApi


// todo add any other module screen here  replace with dependency injection


object DependencyProvider {


    private lateinit var communityFeatureApi: CommunityFeatureApi


    fun provideImpl(
        communityFeatureApi: CommunityFeatureApi,

        ) {

        DependencyProvider.communityFeatureApi = communityFeatureApi

    }


    fun communityFeature(): CommunityFeatureApi = communityFeatureApi


}