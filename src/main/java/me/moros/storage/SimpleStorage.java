package me.moros.storage;

import com.zaxxer.hikari.HikariDataSource;

record SimpleStorage(StorageType type, HikariDataSource source) implements StorageDataSource {
}
