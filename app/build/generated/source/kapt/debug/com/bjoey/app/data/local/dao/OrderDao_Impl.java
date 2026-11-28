package com.bjoey.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bjoey.app.data.local.entity.DiningOrderEntity;
import com.bjoey.app.data.local.entity.DiningOrderItemEntity;
import com.bjoey.app.data.local.entity.DiningOrderWithItems;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DiningOrderEntity> __insertionAdapterOfDiningOrderEntity;

  private final EntityInsertionAdapter<DiningOrderItemEntity> __insertionAdapterOfDiningOrderItemEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteItemsByOrderId;

  private final SharedSQLiteStatement __preparedStmtOfMarkOrderPaid;

  private final SharedSQLiteStatement __preparedStmtOfMarkOrderCancelled;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDiningOrderEntity = new EntityInsertionAdapter<DiningOrderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dining_orders` (`id`,`orderNumber`,`tableId`,`tableName`,`guestCount`,`shiftSessionId`,`status`,`paymentMethod`,`subtotal`,`taxAmount`,`grandTotal`,`cashReceived`,`changeAmount`,`createdAt`,`updatedAt`,`closedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DiningOrderEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        statement.bindLong(2, entity.getOrderNumber());
        if (entity.getTableId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTableId());
        }
        if (entity.getTableName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTableName());
        }
        statement.bindLong(5, entity.getGuestCount());
        if (entity.getShiftSessionId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getShiftSessionId());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getStatus());
        }
        if (entity.getPaymentMethod() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPaymentMethod());
        }
        statement.bindLong(9, entity.getSubtotal());
        statement.bindLong(10, entity.getTaxAmount());
        statement.bindLong(11, entity.getGrandTotal());
        if (entity.getCashReceived() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getCashReceived());
        }
        if (entity.getChangeAmount() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getChangeAmount());
        }
        statement.bindLong(14, entity.getCreatedAt());
        statement.bindLong(15, entity.getUpdatedAt());
        if (entity.getClosedAt() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getClosedAt());
        }
      }
    };
    this.__insertionAdapterOfDiningOrderItemEntity = new EntityInsertionAdapter<DiningOrderItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `dining_order_items` (`id`,`orderId`,`menuItemId`,`itemTitle`,`unitPrice`,`quantity`,`categoryName`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DiningOrderItemEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getOrderId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getOrderId());
        }
        if (entity.getMenuItemId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getMenuItemId());
        }
        if (entity.getItemTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getItemTitle());
        }
        statement.bindLong(5, entity.getUnitPrice());
        statement.bindLong(6, entity.getQuantity());
        if (entity.getCategoryName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCategoryName());
        }
      }
    };
    this.__preparedStmtOfDeleteItemsByOrderId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM dining_order_items WHERE orderId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkOrderPaid = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE dining_orders\n"
                + "        SET status = 'PAID',\n"
                + "            paymentMethod = ?,\n"
                + "            subtotal = ?,\n"
                + "            taxAmount = ?,\n"
                + "            grandTotal = ?,\n"
                + "            cashReceived = ?,\n"
                + "            changeAmount = ?,\n"
                + "            updatedAt = ?,\n"
                + "            closedAt = ?\n"
                + "        WHERE id = ?\n"
                + "        ";
        return _query;
      }
    };
    this.__preparedStmtOfMarkOrderCancelled = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE dining_orders\n"
                + "        SET status = 'CANCELLED',\n"
                + "            updatedAt = ?,\n"
                + "            closedAt = ?\n"
                + "        WHERE id = ?\n"
                + "        ";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final DiningOrderEntity order,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDiningOrderEntity.insert(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrderItems(final List<DiningOrderItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDiningOrderItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object saveOrderWithItems(final DiningOrderEntity order,
      final List<DiningOrderItemEntity> items, final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> OrderDao.DefaultImpls.saveOrderWithItems(OrderDao_Impl.this, order, items, __cont), $completion);
  }

  @Override
  public Object deleteItemsByOrderId(final String orderId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteItemsByOrderId.acquire();
        int _argIndex = 1;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteItemsByOrderId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markOrderPaid(final String orderId, final String paymentMethod, final long subtotal,
      final long taxAmount, final long grandTotal, final Long cashReceived, final Long changeAmount,
      final long closedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkOrderPaid.acquire();
        int _argIndex = 1;
        if (paymentMethod == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, paymentMethod);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, subtotal);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, taxAmount);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, grandTotal);
        _argIndex = 5;
        if (cashReceived == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, cashReceived);
        }
        _argIndex = 6;
        if (changeAmount == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, changeAmount);
        }
        _argIndex = 7;
        _stmt.bindLong(_argIndex, closedAt);
        _argIndex = 8;
        _stmt.bindLong(_argIndex, closedAt);
        _argIndex = 9;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkOrderPaid.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markOrderCancelled(final String orderId, final long closedAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkOrderCancelled.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, closedAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, closedAt);
        _argIndex = 3;
        if (orderId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, orderId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkOrderCancelled.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DiningOrderWithItems>> observeOpenOrdersWithItems() {
    final String _sql = "SELECT * FROM dining_orders WHERE status = 'OPEN' ORDER BY updatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"dining_order_items",
        "dining_orders"}, new Callable<List<DiningOrderWithItems>>() {
      @Override
      @NonNull
      public List<DiningOrderWithItems> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "orderNumber");
            final int _cursorIndexOfTableId = CursorUtil.getColumnIndexOrThrow(_cursor, "tableId");
            final int _cursorIndexOfTableName = CursorUtil.getColumnIndexOrThrow(_cursor, "tableName");
            final int _cursorIndexOfGuestCount = CursorUtil.getColumnIndexOrThrow(_cursor, "guestCount");
            final int _cursorIndexOfShiftSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "shiftSessionId");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
            final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
            final int _cursorIndexOfTaxAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "taxAmount");
            final int _cursorIndexOfGrandTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "grandTotal");
            final int _cursorIndexOfCashReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "cashReceived");
            final int _cursorIndexOfChangeAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "changeAmount");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfClosedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "closedAt");
            final ArrayMap<String, ArrayList<DiningOrderItemEntity>> _collectionItems = new ArrayMap<String, ArrayList<DiningOrderItemEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionItems.containsKey(_tmpKey)) {
                  _collectionItems.put(_tmpKey, new ArrayList<DiningOrderItemEntity>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipdiningOrderItemsAscomBjoeyAppDataLocalEntityDiningOrderItemEntity(_collectionItems);
            final List<DiningOrderWithItems> _result = new ArrayList<DiningOrderWithItems>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final DiningOrderWithItems _item;
              final DiningOrderEntity _tmpOrder;
              final String _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getString(_cursorIndexOfId);
              }
              final int _tmpOrderNumber;
              _tmpOrderNumber = _cursor.getInt(_cursorIndexOfOrderNumber);
              final String _tmpTableId;
              if (_cursor.isNull(_cursorIndexOfTableId)) {
                _tmpTableId = null;
              } else {
                _tmpTableId = _cursor.getString(_cursorIndexOfTableId);
              }
              final String _tmpTableName;
              if (_cursor.isNull(_cursorIndexOfTableName)) {
                _tmpTableName = null;
              } else {
                _tmpTableName = _cursor.getString(_cursorIndexOfTableName);
              }
              final int _tmpGuestCount;
              _tmpGuestCount = _cursor.getInt(_cursorIndexOfGuestCount);
              final String _tmpShiftSessionId;
              if (_cursor.isNull(_cursorIndexOfShiftSessionId)) {
                _tmpShiftSessionId = null;
              } else {
                _tmpShiftSessionId = _cursor.getString(_cursorIndexOfShiftSessionId);
              }
              final String _tmpStatus;
              if (_cursor.isNull(_cursorIndexOfStatus)) {
                _tmpStatus = null;
              } else {
                _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
              }
              final String _tmpPaymentMethod;
              if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
                _tmpPaymentMethod = null;
              } else {
                _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
              }
              final long _tmpSubtotal;
              _tmpSubtotal = _cursor.getLong(_cursorIndexOfSubtotal);
              final long _tmpTaxAmount;
              _tmpTaxAmount = _cursor.getLong(_cursorIndexOfTaxAmount);
              final long _tmpGrandTotal;
              _tmpGrandTotal = _cursor.getLong(_cursorIndexOfGrandTotal);
              final Long _tmpCashReceived;
              if (_cursor.isNull(_cursorIndexOfCashReceived)) {
                _tmpCashReceived = null;
              } else {
                _tmpCashReceived = _cursor.getLong(_cursorIndexOfCashReceived);
              }
              final Long _tmpChangeAmount;
              if (_cursor.isNull(_cursorIndexOfChangeAmount)) {
                _tmpChangeAmount = null;
              } else {
                _tmpChangeAmount = _cursor.getLong(_cursorIndexOfChangeAmount);
              }
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final Long _tmpClosedAt;
              if (_cursor.isNull(_cursorIndexOfClosedAt)) {
                _tmpClosedAt = null;
              } else {
                _tmpClosedAt = _cursor.getLong(_cursorIndexOfClosedAt);
              }
              _tmpOrder = new DiningOrderEntity(_tmpId,_tmpOrderNumber,_tmpTableId,_tmpTableName,_tmpGuestCount,_tmpShiftSessionId,_tmpStatus,_tmpPaymentMethod,_tmpSubtotal,_tmpTaxAmount,_tmpGrandTotal,_tmpCashReceived,_tmpChangeAmount,_tmpCreatedAt,_tmpUpdatedAt,_tmpClosedAt);
              final ArrayList<DiningOrderItemEntity> _tmpItemsCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                _tmpItemsCollection = _collectionItems.get(_tmpKey_1);
              } else {
                _tmpItemsCollection = new ArrayList<DiningOrderItemEntity>();
              }
              _item = new DiningOrderWithItems(_tmpOrder,_tmpItemsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<DiningOrderWithItems>> observeAllOrdersWithItems() {
    final String _sql = "SELECT * FROM dining_orders ORDER BY createdAt DESC, orderNumber DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"dining_order_items",
        "dining_orders"}, new Callable<List<DiningOrderWithItems>>() {
      @Override
      @NonNull
      public List<DiningOrderWithItems> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfOrderNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "orderNumber");
            final int _cursorIndexOfTableId = CursorUtil.getColumnIndexOrThrow(_cursor, "tableId");
            final int _cursorIndexOfTableName = CursorUtil.getColumnIndexOrThrow(_cursor, "tableName");
            final int _cursorIndexOfGuestCount = CursorUtil.getColumnIndexOrThrow(_cursor, "guestCount");
            final int _cursorIndexOfShiftSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "shiftSessionId");
            final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
            final int _cursorIndexOfPaymentMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "paymentMethod");
            final int _cursorIndexOfSubtotal = CursorUtil.getColumnIndexOrThrow(_cursor, "subtotal");
            final int _cursorIndexOfTaxAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "taxAmount");
            final int _cursorIndexOfGrandTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "grandTotal");
            final int _cursorIndexOfCashReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "cashReceived");
            final int _cursorIndexOfChangeAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "changeAmount");
            final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
            final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
            final int _cursorIndexOfClosedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "closedAt");
            final ArrayMap<String, ArrayList<DiningOrderItemEntity>> _collectionItems = new ArrayMap<String, ArrayList<DiningOrderItemEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getString(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionItems.containsKey(_tmpKey)) {
                  _collectionItems.put(_tmpKey, new ArrayList<DiningOrderItemEntity>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipdiningOrderItemsAscomBjoeyAppDataLocalEntityDiningOrderItemEntity(_collectionItems);
            final List<DiningOrderWithItems> _result = new ArrayList<DiningOrderWithItems>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final DiningOrderWithItems _item;
              final DiningOrderEntity _tmpOrder;
              final String _tmpId;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpId = null;
              } else {
                _tmpId = _cursor.getString(_cursorIndexOfId);
              }
              final int _tmpOrderNumber;
              _tmpOrderNumber = _cursor.getInt(_cursorIndexOfOrderNumber);
              final String _tmpTableId;
              if (_cursor.isNull(_cursorIndexOfTableId)) {
                _tmpTableId = null;
              } else {
                _tmpTableId = _cursor.getString(_cursorIndexOfTableId);
              }
              final String _tmpTableName;
              if (_cursor.isNull(_cursorIndexOfTableName)) {
                _tmpTableName = null;
              } else {
                _tmpTableName = _cursor.getString(_cursorIndexOfTableName);
              }
              final int _tmpGuestCount;
              _tmpGuestCount = _cursor.getInt(_cursorIndexOfGuestCount);
              final String _tmpShiftSessionId;
              if (_cursor.isNull(_cursorIndexOfShiftSessionId)) {
                _tmpShiftSessionId = null;
              } else {
                _tmpShiftSessionId = _cursor.getString(_cursorIndexOfShiftSessionId);
              }
              final String _tmpStatus;
              if (_cursor.isNull(_cursorIndexOfStatus)) {
                _tmpStatus = null;
              } else {
                _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
              }
              final String _tmpPaymentMethod;
              if (_cursor.isNull(_cursorIndexOfPaymentMethod)) {
                _tmpPaymentMethod = null;
              } else {
                _tmpPaymentMethod = _cursor.getString(_cursorIndexOfPaymentMethod);
              }
              final long _tmpSubtotal;
              _tmpSubtotal = _cursor.getLong(_cursorIndexOfSubtotal);
              final long _tmpTaxAmount;
              _tmpTaxAmount = _cursor.getLong(_cursorIndexOfTaxAmount);
              final long _tmpGrandTotal;
              _tmpGrandTotal = _cursor.getLong(_cursorIndexOfGrandTotal);
              final Long _tmpCashReceived;
              if (_cursor.isNull(_cursorIndexOfCashReceived)) {
                _tmpCashReceived = null;
              } else {
                _tmpCashReceived = _cursor.getLong(_cursorIndexOfCashReceived);
              }
              final Long _tmpChangeAmount;
              if (_cursor.isNull(_cursorIndexOfChangeAmount)) {
                _tmpChangeAmount = null;
              } else {
                _tmpChangeAmount = _cursor.getLong(_cursorIndexOfChangeAmount);
              }
              final long _tmpCreatedAt;
              _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
              final long _tmpUpdatedAt;
              _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
              final Long _tmpClosedAt;
              if (_cursor.isNull(_cursorIndexOfClosedAt)) {
                _tmpClosedAt = null;
              } else {
                _tmpClosedAt = _cursor.getLong(_cursorIndexOfClosedAt);
              }
              _tmpOrder = new DiningOrderEntity(_tmpId,_tmpOrderNumber,_tmpTableId,_tmpTableName,_tmpGuestCount,_tmpShiftSessionId,_tmpStatus,_tmpPaymentMethod,_tmpSubtotal,_tmpTaxAmount,_tmpGrandTotal,_tmpCashReceived,_tmpChangeAmount,_tmpCreatedAt,_tmpUpdatedAt,_tmpClosedAt);
              final ArrayList<DiningOrderItemEntity> _tmpItemsCollection;
              final String _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getString(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                _tmpItemsCollection = _collectionItems.get(_tmpKey_1);
              } else {
                _tmpItemsCollection = new ArrayList<DiningOrderItemEntity>();
              }
              _item = new DiningOrderWithItems(_tmpOrder,_tmpItemsCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getNextOrderNumber(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COALESCE(MAX(orderNumber), 0) + 1 FROM dining_orders";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipdiningOrderItemsAscomBjoeyAppDataLocalEntityDiningOrderItemEntity(
      @NonNull final ArrayMap<String, ArrayList<DiningOrderItemEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipdiningOrderItemsAscomBjoeyAppDataLocalEntityDiningOrderItemEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`orderId`,`menuItemId`,`itemTitle`,`unitPrice`,`quantity`,`categoryName` FROM `dining_order_items` WHERE `orderId` IN (");
    final int _inputSize = __mapKeySet == null ? 1 : __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    if (__mapKeySet == null) {
      _stmt.bindNull(_argIndex);
    } else {
      for (String _item : __mapKeySet) {
        if (_item == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _item);
        }
        _argIndex++;
      }
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "orderId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfOrderId = 1;
      final int _cursorIndexOfMenuItemId = 2;
      final int _cursorIndexOfItemTitle = 3;
      final int _cursorIndexOfUnitPrice = 4;
      final int _cursorIndexOfQuantity = 5;
      final int _cursorIndexOfCategoryName = 6;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        if (_cursor.isNull(_itemKeyIndex)) {
          _tmpKey = null;
        } else {
          _tmpKey = _cursor.getString(_itemKeyIndex);
        }
        if (_tmpKey != null) {
          final ArrayList<DiningOrderItemEntity> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final DiningOrderItemEntity _item_1;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpOrderId;
            if (_cursor.isNull(_cursorIndexOfOrderId)) {
              _tmpOrderId = null;
            } else {
              _tmpOrderId = _cursor.getString(_cursorIndexOfOrderId);
            }
            final String _tmpMenuItemId;
            if (_cursor.isNull(_cursorIndexOfMenuItemId)) {
              _tmpMenuItemId = null;
            } else {
              _tmpMenuItemId = _cursor.getString(_cursorIndexOfMenuItemId);
            }
            final String _tmpItemTitle;
            if (_cursor.isNull(_cursorIndexOfItemTitle)) {
              _tmpItemTitle = null;
            } else {
              _tmpItemTitle = _cursor.getString(_cursorIndexOfItemTitle);
            }
            final long _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getLong(_cursorIndexOfUnitPrice);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final String _tmpCategoryName;
            if (_cursor.isNull(_cursorIndexOfCategoryName)) {
              _tmpCategoryName = null;
            } else {
              _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            }
            _item_1 = new DiningOrderItemEntity(_tmpId,_tmpOrderId,_tmpMenuItemId,_tmpItemTitle,_tmpUnitPrice,_tmpQuantity,_tmpCategoryName);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
