package com.example.daggerhiltmvvm.utils

import androidx.lifecycle.ViewModel
import androidx.room.Entity

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity) : DomainModel

    fun mapToEntity(domainModel: DomainModel) : Entity
}