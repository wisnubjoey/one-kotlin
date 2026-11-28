package com.bjoey.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.bjoey.app.data.local.dao.MenuDao;
import com.bjoey.app.data.local.dao.MenuDao_Impl;
import com.bjoey.app.data.local.dao.OrderDao;
import com.bjoey.app.data.local.dao.OrderDao_Impl;
import com.bjoey.app.data.local.dao.ShiftDao;
import com.bjoey.app.data.local.dao.ShiftDao_Impl;
import com.bjoey.app.data.local.dao.TableDao;
import com.bjoey.app.data.local.dao.TableDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ShiftDao _shiftDao;

  private volatile MenuDao _menuDao;

  private volatile TableDao _tableDao;

  private volatile OrderDao _orderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(5) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `shifts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `pin` TEXT NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `shift_sessions` (`id` TEXT NOT NULL, `shiftId` INTEGER NOT NULL, `openedAt` INTEGER NOT NULL, `closedAt` INTEGER, `isSynced` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `menu_categories` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `menu_items` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `price` INTEGER NOT NULL, `categoryId` TEXT NOT NULL, `imageUri` TEXT, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`categoryId`) REFERENCES `menu_categories`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_menu_items_categoryId` ON `menu_items` (`categoryId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dining_tables` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dining_orders` (`id` TEXT NOT NULL, `orderNumber` INTEGER NOT NULL, `tableId` TEXT NOT NULL, `tableName` TEXT NOT NULL, `guestCount` INTEGER NOT NULL, `shiftSessionId` TEXT NOT NULL, `status` TEXT NOT NULL, `paymentMethod` TEXT, `subtotal` INTEGER NOT NULL, `taxAmount` INTEGER NOT NULL, `grandTotal` INTEGER NOT NULL, `cashReceived` INTEGER, `changeAmount` INTEGER, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `closedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_dining_orders_tableId` ON `dining_orders` (`tableId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_dining_orders_status` ON `dining_orders` (`status`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `dining_order_items` (`id` TEXT NOT NULL, `orderId` TEXT NOT NULL, `menuItemId` TEXT NOT NULL, `itemTitle` TEXT NOT NULL, `unitPrice` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, `categoryName` TEXT NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`orderId`) REFERENCES `dining_orders`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_dining_order_items_orderId` ON `dining_order_items` (`orderId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'b2c8415c734cc4271c905aa83b28850a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `shifts`");
        db.execSQL("DROP TABLE IF EXISTS `shift_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `menu_categories`");
        db.execSQL("DROP TABLE IF EXISTS `menu_items`");
        db.execSQL("DROP TABLE IF EXISTS `dining_tables`");
        db.execSQL("DROP TABLE IF EXISTS `dining_orders`");
        db.execSQL("DROP TABLE IF EXISTS `dining_order_items`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsShifts = new HashMap<String, TableInfo.Column>(4);
        _columnsShifts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("pin", new TableInfo.Column("pin", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShifts.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShifts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesShifts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoShifts = new TableInfo("shifts", _columnsShifts, _foreignKeysShifts, _indicesShifts);
        final TableInfo _existingShifts = TableInfo.read(db, "shifts");
        if (!_infoShifts.equals(_existingShifts)) {
          return new RoomOpenHelper.ValidationResult(false, "shifts(com.bjoey.app.data.local.entity.ShiftEntity).\n"
                  + " Expected:\n" + _infoShifts + "\n"
                  + " Found:\n" + _existingShifts);
        }
        final HashMap<String, TableInfo.Column> _columnsShiftSessions = new HashMap<String, TableInfo.Column>(5);
        _columnsShiftSessions.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftSessions.put("shiftId", new TableInfo.Column("shiftId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftSessions.put("openedAt", new TableInfo.Column("openedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftSessions.put("closedAt", new TableInfo.Column("closedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsShiftSessions.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysShiftSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesShiftSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoShiftSessions = new TableInfo("shift_sessions", _columnsShiftSessions, _foreignKeysShiftSessions, _indicesShiftSessions);
        final TableInfo _existingShiftSessions = TableInfo.read(db, "shift_sessions");
        if (!_infoShiftSessions.equals(_existingShiftSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "shift_sessions(com.bjoey.app.data.local.entity.ShiftSessionEntity).\n"
                  + " Expected:\n" + _infoShiftSessions + "\n"
                  + " Found:\n" + _existingShiftSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsMenuCategories = new HashMap<String, TableInfo.Column>(3);
        _columnsMenuCategories.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuCategories.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuCategories.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMenuCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMenuCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMenuCategories = new TableInfo("menu_categories", _columnsMenuCategories, _foreignKeysMenuCategories, _indicesMenuCategories);
        final TableInfo _existingMenuCategories = TableInfo.read(db, "menu_categories");
        if (!_infoMenuCategories.equals(_existingMenuCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "menu_categories(com.bjoey.app.data.local.entity.MenuCategoryEntity).\n"
                  + " Expected:\n" + _infoMenuCategories + "\n"
                  + " Found:\n" + _existingMenuCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsMenuItems = new HashMap<String, TableInfo.Column>(6);
        _columnsMenuItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("price", new TableInfo.Column("price", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("categoryId", new TableInfo.Column("categoryId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("imageUri", new TableInfo.Column("imageUri", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMenuItems.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMenuItems = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysMenuItems.add(new TableInfo.ForeignKey("menu_categories", "RESTRICT", "NO ACTION", Arrays.asList("categoryId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesMenuItems = new HashSet<TableInfo.Index>(1);
        _indicesMenuItems.add(new TableInfo.Index("index_menu_items_categoryId", false, Arrays.asList("categoryId"), Arrays.asList("ASC")));
        final TableInfo _infoMenuItems = new TableInfo("menu_items", _columnsMenuItems, _foreignKeysMenuItems, _indicesMenuItems);
        final TableInfo _existingMenuItems = TableInfo.read(db, "menu_items");
        if (!_infoMenuItems.equals(_existingMenuItems)) {
          return new RoomOpenHelper.ValidationResult(false, "menu_items(com.bjoey.app.data.local.entity.MenuItemEntity).\n"
                  + " Expected:\n" + _infoMenuItems + "\n"
                  + " Found:\n" + _existingMenuItems);
        }
        final HashMap<String, TableInfo.Column> _columnsDiningTables = new HashMap<String, TableInfo.Column>(3);
        _columnsDiningTables.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningTables.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningTables.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDiningTables = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDiningTables = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDiningTables = new TableInfo("dining_tables", _columnsDiningTables, _foreignKeysDiningTables, _indicesDiningTables);
        final TableInfo _existingDiningTables = TableInfo.read(db, "dining_tables");
        if (!_infoDiningTables.equals(_existingDiningTables)) {
          return new RoomOpenHelper.ValidationResult(false, "dining_tables(com.bjoey.app.data.local.entity.DiningTableEntity).\n"
                  + " Expected:\n" + _infoDiningTables + "\n"
                  + " Found:\n" + _existingDiningTables);
        }
        final HashMap<String, TableInfo.Column> _columnsDiningOrders = new HashMap<String, TableInfo.Column>(16);
        _columnsDiningOrders.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("orderNumber", new TableInfo.Column("orderNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("tableId", new TableInfo.Column("tableId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("tableName", new TableInfo.Column("tableName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("guestCount", new TableInfo.Column("guestCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("shiftSessionId", new TableInfo.Column("shiftSessionId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("paymentMethod", new TableInfo.Column("paymentMethod", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("subtotal", new TableInfo.Column("subtotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("taxAmount", new TableInfo.Column("taxAmount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("grandTotal", new TableInfo.Column("grandTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("cashReceived", new TableInfo.Column("cashReceived", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("changeAmount", new TableInfo.Column("changeAmount", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrders.put("closedAt", new TableInfo.Column("closedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDiningOrders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDiningOrders = new HashSet<TableInfo.Index>(2);
        _indicesDiningOrders.add(new TableInfo.Index("index_dining_orders_tableId", false, Arrays.asList("tableId"), Arrays.asList("ASC")));
        _indicesDiningOrders.add(new TableInfo.Index("index_dining_orders_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        final TableInfo _infoDiningOrders = new TableInfo("dining_orders", _columnsDiningOrders, _foreignKeysDiningOrders, _indicesDiningOrders);
        final TableInfo _existingDiningOrders = TableInfo.read(db, "dining_orders");
        if (!_infoDiningOrders.equals(_existingDiningOrders)) {
          return new RoomOpenHelper.ValidationResult(false, "dining_orders(com.bjoey.app.data.local.entity.DiningOrderEntity).\n"
                  + " Expected:\n" + _infoDiningOrders + "\n"
                  + " Found:\n" + _existingDiningOrders);
        }
        final HashMap<String, TableInfo.Column> _columnsDiningOrderItems = new HashMap<String, TableInfo.Column>(7);
        _columnsDiningOrderItems.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("orderId", new TableInfo.Column("orderId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("menuItemId", new TableInfo.Column("menuItemId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("itemTitle", new TableInfo.Column("itemTitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("unitPrice", new TableInfo.Column("unitPrice", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDiningOrderItems.put("categoryName", new TableInfo.Column("categoryName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDiningOrderItems = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDiningOrderItems.add(new TableInfo.ForeignKey("dining_orders", "CASCADE", "NO ACTION", Arrays.asList("orderId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesDiningOrderItems = new HashSet<TableInfo.Index>(1);
        _indicesDiningOrderItems.add(new TableInfo.Index("index_dining_order_items_orderId", false, Arrays.asList("orderId"), Arrays.asList("ASC")));
        final TableInfo _infoDiningOrderItems = new TableInfo("dining_order_items", _columnsDiningOrderItems, _foreignKeysDiningOrderItems, _indicesDiningOrderItems);
        final TableInfo _existingDiningOrderItems = TableInfo.read(db, "dining_order_items");
        if (!_infoDiningOrderItems.equals(_existingDiningOrderItems)) {
          return new RoomOpenHelper.ValidationResult(false, "dining_order_items(com.bjoey.app.data.local.entity.DiningOrderItemEntity).\n"
                  + " Expected:\n" + _infoDiningOrderItems + "\n"
                  + " Found:\n" + _existingDiningOrderItems);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "b2c8415c734cc4271c905aa83b28850a", "008aa8bd1c6e91d8ed38762b7a632e67");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "shifts","shift_sessions","menu_categories","menu_items","dining_tables","dining_orders","dining_order_items");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `shifts`");
      _db.execSQL("DELETE FROM `shift_sessions`");
      _db.execSQL("DELETE FROM `menu_items`");
      _db.execSQL("DELETE FROM `menu_categories`");
      _db.execSQL("DELETE FROM `dining_tables`");
      _db.execSQL("DELETE FROM `dining_orders`");
      _db.execSQL("DELETE FROM `dining_order_items`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ShiftDao.class, ShiftDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MenuDao.class, MenuDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TableDao.class, TableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ShiftDao shiftDao() {
    if (_shiftDao != null) {
      return _shiftDao;
    } else {
      synchronized(this) {
        if(_shiftDao == null) {
          _shiftDao = new ShiftDao_Impl(this);
        }
        return _shiftDao;
      }
    }
  }

  @Override
  public MenuDao menuDao() {
    if (_menuDao != null) {
      return _menuDao;
    } else {
      synchronized(this) {
        if(_menuDao == null) {
          _menuDao = new MenuDao_Impl(this);
        }
        return _menuDao;
      }
    }
  }

  @Override
  public TableDao tableDao() {
    if (_tableDao != null) {
      return _tableDao;
    } else {
      synchronized(this) {
        if(_tableDao == null) {
          _tableDao = new TableDao_Impl(this);
        }
        return _tableDao;
      }
    }
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }
}
