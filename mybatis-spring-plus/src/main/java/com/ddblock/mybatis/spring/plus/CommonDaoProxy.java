package com.ddblock.mybatis.spring.plus;

import com.ddblock.mybatis.spring.plus.mapper.CommonMapper;
import com.ddblock.mybatis.spring.plus.mapper.CommonMapperResultHandler;
import com.ddblock.mybatis.spring.plus.mapper.support.Order;
import com.ddblock.mybatis.spring.plus.mapper.support.Page;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 通用DAO的代理实现类
 *
 * Author XiaoJia
 * Date 2019-03-07 8:13
 */
public class CommonDaoProxy<T> implements CommonDao<T> {

    private Class<T> table;
    private CommonMapper mapper;

    public CommonDaoProxy(Class<T> table) {
        this.table = table;
    }

    public void setMapper(CommonMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int add(T model) {
        return mapper.add(model);
    }

    @Override
    public int update(T model, boolean doNull) {
        return mapper.update(model, doNull);
    }

    @Override
    public int updateBatch(T setData, T whereData, boolean doNull) {
        return mapper.updateBatch(setData, whereData, doNull);
    }

    @Override
    public int delete(Serializable id) {
        return mapper.delete(table, id);
    }

    @Override
    public int deleteBatch(T model, boolean doNull) {
        return mapper.deleteBatch(model, doNull);
    }

    @Override
    public T searchOne(Serializable id) {
        CommonMapperResultHandler<T> resultHandler = new CommonMapperResultHandler<>(table);
        mapper.searchOne(table, id, resultHandler);
        List<T> dataList = resultHandler.getDataList();
        if (dataList.size() > 1) {
            throw new RuntimeException("根据主键：" + id + "，查询到多条数据：" + Arrays.toString(dataList.toArray()));
        }
        return dataList.isEmpty() ? null : dataList.get(0);
    }

    @Override
    public List<T> searchList(T whereData, Order... orders) {
        CommonMapperResultHandler<T> resultHandler = new CommonMapperResultHandler<>(table);
        mapper.searchList(whereData, resultHandler, orders);
        return resultHandler.getDataList();
    }

    @Override
    public List<T> searchListBySQL(SQL sql) {
        CommonMapperResultHandler<T> resultHandler = new CommonMapperResultHandler<>(table);
        mapper.searchListBySQL(sql, resultHandler);
        return resultHandler.getDataList();
    }

    @Override
    public List<T> searchAll(Order... orders) {
        CommonMapperResultHandler<T> resultHandler = new CommonMapperResultHandler<>(table);
        mapper.searchAll(table, resultHandler, orders);
        return resultHandler.getDataList();
    }

    @Override
    public Page<T> searchAllByPage(Page<T> page, Order... orders) {
        CommonMapperResultHandler<T> resultHandler = new CommonMapperResultHandler<>(table);
        mapper.searchAllByPage(table, page, resultHandler, orders);
        page.setResults(resultHandler.getDataList());
        return page;
    }

    @Override
    public long searchCount(T whereData) {
        return mapper.searchCount(whereData);
    }

    @Override
    public long searchAllCount() {
        return mapper.searchAllCount(table);
    }
}
