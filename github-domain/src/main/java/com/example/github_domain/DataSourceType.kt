package com.example.github_domain

sealed class DataSourceType

data object Api : DataSourceType()
data object Local : DataSourceType()
