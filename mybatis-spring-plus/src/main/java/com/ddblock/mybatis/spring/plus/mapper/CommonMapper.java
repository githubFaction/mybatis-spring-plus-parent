package com.ddblock.mybatis.spring.plus.mapper;

import com.ddblock.mybatis.spring.plus.mapper.support.Order;
import com.ddblock.mybatis.spring.plus.mapper.support.Page;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;

/**
 * 操作DB的通用处理Mapper
 *
 * Author XiaoJia
 * Date 2019-03-04 17:47
 */
public interface CommonMapper {

    /**
     * 添加一条记录
     *
     * @param model 记录
     *
     * @return int 返回的变更条数
     */
    @InsertProvider(type = CommonMapperProvider.class, method = "add")
    <T> int add(T model);

    /**
     * 根据 id 更新对应的model
     *
     * @param model  变更记录
     * @param doNull 字段值为空是否处理
     *
     * @return int 返回的变更条数
     */
    @UpdateProvider(type = CommonMapperProvider.class, method = "update")
    <T> int update(T model, boolean doNull);

    /**
     * 根据对象值条件，批量更新符合条件的记录集合
     *
     * @param setData   变更之后的数据
     * @param whereData 变更条件（注意：字段为空不作为Where条件）
     * @param doNull    字段值为空是否处理
     *
     * @return int 返回的变更条数
     */
    @UpdateProvider(type = CommonMapperProvider.class, method = "updateBatch")
    <T> int updateBatch(T setData, T whereData, boolean doNull);

    /**
     * 根据 id 删除一条数据
     *
     * @param table 表结构类
     * @param id    记录ID
     * @param <T>   表结构
     *
     * @return 返回的变更条数
     */
    @DeleteProvider(type = CommonMapperProvider.class, method = "delete")
    <T> int delete(Class<T> table, Serializable id);

    /**
     * 根据对象值条件，批量删除符合条件的记录集合
     *
     * @param whereData 变更条件
     * @param doNull    字段值为空是否处理
     *
     * @return int 返回的变更条数
     */
    @DeleteProvider(type = CommonMapperProvider.class, method = "deleteBatch")
    <T> int deleteBatch(T whereData, boolean doNull);

    /**
     * 根据id查询一条记录
     *
     * @param table         表结构类
     * @param id            记录ID
     * @param resultHandler 结果类型处理器
     * @param <T>           表结构
     */
    @ResultType(value = CommonMapperResultType.class)
    @SelectProvider(type = CommonMapperProvider.class, method = "searchOne")
    <T> void searchOne(Class<T> table, Serializable id, CommonMapperResultHandler resultHandler);

    /**
     * 根据对象值条件，查询符合条件的记录集合
     *
     * @param whereData     查询条件（注意：字段为空不作为Where条件）
     * @param resultHandler 结果类型处理器
     * @param orders        排序规则
     * @param <T>           表结构
     */
    @ResultType(value = CommonMapperResultType.class)
    @SelectProvider(type = CommonMapperProvider.class, method = "searchList")
    <T> void searchList(T whereData, CommonMapperResultHandler resultHandler, Order... orders);

    /**
     * 根据自定义SQL，查询符合条件的记录集合
     *
     * @param sql           查询SQL
     * @param resultHandler 结果类型处理器
     * @param <T>           表结构
     */
    @ResultType(value = CommonMapperResultType.class)
    @SelectProvider(type = CommonMapperProvider.class, method = "searchListBySQL")
    <T> void searchListBySQL(SQL sql, CommonMapperResultHandler resultHandler);

    /**
     * 按照排序规则将表中数据全部查询出来
     *
     * @param table         表结构类
     * @param resultHandler 结果类型处理器
     * @param orders        排序规则
     * @param <T>           表结构
     */
    @ResultType(value = CommonMapperResultType.class)
    @SelectProvider(type = CommonMapperProvider.class, method = "searchAll")
    <T> void searchAll(Class<T> table, CommonMapperResultHandler resultHandler, Order... orders);

    /**
     * 按照排序规则将表中数据分页查询出来
     *
     * @param table         表结构类
     * @param page          分页信息
     * @param resultHandler 结果类型处理器
     * @param orders        培训规则
     * @param <T>           表结构
     */
    @ResultType(value = CommonMapperResultType.class)
    @SelectProvider(type = CommonMapperProvider.class, method = "searchAllByPage")
    <T> void searchAllByPage(Class<T> table, Page<T> page, CommonMapperResultHandler resultHandler, Order... orders);

    /**
     * 获取符合条件的总记录数
     *
     * @param whereData 查询条件（注意：字段为空不作为Where条件）
     * @param <T>       表结构
     *
     * @return 记录数
     */
    @SelectProvider(type = CommonMapperProvider.class, method = "searchCount")
    <T> long searchCount(T whereData);

    /**
     * 获取总记录数
     *
     * @param table 表结构类
     * @param <T>   表结构
     *
     * @return 总记录数
     */
    @SelectProvider(type = CommonMapperProvider.class, method = "searchAllCount")
    <T> long searchAllCount(Class<T> table);
}
