package com.sdl.ecommerce.odata.datasource;

import com.sdl.ecommerce.api.ProductCategoryService;
import com.sdl.ecommerce.odata.model.ODataCategory;
import com.sdl.odata.api.ODataException;
import com.sdl.odata.api.ODataSystemException;
import com.sdl.odata.api.processor.datasource.DataSource;
import com.sdl.odata.api.processor.datasource.ODataDataSourceException;
import com.sdl.odata.api.processor.query.*;
import com.sdl.odata.api.processor.query.strategy.QueryOperationStrategy;
import com.sdl.odata.api.service.ODataRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CategoryDataSourceProvider
 *
 * @author nic
 */
@Component
public class CategoryDataSourceProvider extends ECommerceDataSourceProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryDataSourceProvider.class);

    @Autowired
    private ProductCategoryService categoryService;

    @Override
    public DataSource getDataSource(ODataRequestContext oDataRequestContext) {
        return null;
    }

    @Override
    public boolean isSuitableFor(ODataRequestContext oDataRequestContext, String entityType) throws ODataDataSourceException {
        return oDataRequestContext.getEntityDataModel().getType(entityType).getJavaType().equals(ODataCategory.class);
    }

    @Override
    public QueryOperationStrategy getStrategy(QueryOperation queryOperation) throws ODataException {

        List<ODataCategory> categories = new ArrayList<>();

        if (queryOperation instanceof SelectOperation) {
            // TODO: Fetch all categories here

            List<com.sdl.ecommerce.api.model.Category> topLevelCategories = this.categoryService.getTopLevelCategories();
            for (com.sdl.ecommerce.api.model.Category category : topLevelCategories ) {
                categories.add(new ODataCategory(category));
            }

           // buildFromSelect((SelectOperation) operation);
        }
        else if ( queryOperation instanceof SelectByKeyOperation) {
            Map<String, Object> keys = ((SelectByKeyOperation) queryOperation).getKeyAsJava();
            String id = (String)keys.get("id");
            com.sdl.ecommerce.api.model.Category category = this.categoryService.getCategoryById(id);
            if ( category != null ) {
                categories.add(new ODataCategory(category));
            }
        }
        else if ( queryOperation instanceof CriteriaFilterOperation) {

            Criteria criteria = ((CriteriaFilterOperation) queryOperation).getCriteria();
            if(criteria instanceof ComparisonCriteria) {
                ComparisonCriteria comparisonCriteria = (ComparisonCriteria) criteria;

                //For now we only support here property key/value comparisons
                if(comparisonCriteria.getLeft() instanceof PropertyCriteriaValue
                        && comparisonCriteria.getRight() instanceof LiteralCriteriaValue) {

                    PropertyCriteriaValue propertyCriteriaValue = (PropertyCriteriaValue) comparisonCriteria.getLeft();
                    LiteralCriteriaValue literalCriteriaValue = (LiteralCriteriaValue) comparisonCriteria.getRight();
                    if ( propertyCriteriaValue.getPropertyName().equals("path") ) {
                        com.sdl.ecommerce.api.model.Category category = this.categoryService.getCategoryByPath((String) literalCriteriaValue.getValue());
                        if ( category != null ) {
                            categories.add(new ODataCategory(category));
                        }
                    }
                }
            }
        }
        else if ( queryOperation instanceof JoinOperation ) {
            JoinOperation joinOperation = (JoinOperation) queryOperation;
            if ( joinOperation.leftSource() instanceof SelectByKeyOperation &&
                    joinOperation.rightSource() instanceof SelectOperation )
            {
                SelectByKeyOperation left = (SelectByKeyOperation) joinOperation.getLeftSource();
                SelectOperation right = (SelectOperation) joinOperation.getRightSource();
                if ( right.getEntitySetName().equals("Categories") ) {
                    Map<String, Object> keys = left.getKeyAsJava();
                    String id = (String) keys.get("id");
                    com.sdl.ecommerce.api.model.Category category = this.categoryService.getCategoryById(id);
                    if (category != null) {
                        if ( joinOperation.getJoinPropertyName().equals("categories") ) {
                            if ( category.getCategories() != null ) {
                                for (com.sdl.ecommerce.api.model.Category subCategory : category.getCategories()) {
                                    categories.add(new ODataCategory(subCategory));
                                }
                            }
                        }
                        else if ( joinOperation.getJoinPropertyName().equals("parent") ) {
                            if ( category.getParent() != null ) {
                                categories.add(new ODataCategory(category.getParent()));
                            }
                        }
                        else {
                            throw new ODataSystemException("Unknown property name: " + joinOperation.getJoinPropertyName());
                        }
                    }
                }
            }
            else {
                throw new ODataSystemException("Unsopported join operation:" + joinOperation);
            }

            // TODO: Support ExpandOperation: com.sdl.odata.api.ODataSystemException: Unsupported query operation: ExpandOperation(SelectByKeyOperation(SelectOperation(Categories,true),Map(id -> catalog01_18661)),List(categories))
        }
        /*
        else if (operation instanceof LimitOperation) {
            buildFromLimit((LimitOperation) operation);
        } else if (operation instanceof SkipOperation) {
            //not supported for now
        } else if (operation instanceof ExpandOperation) {
            //not supported for now
        } else if (operation instanceof OrderByOperation) {
            //not supported for now
        } else if (operation instanceof SelectPropertiesOperation) {
            //not supported for now
        }
        */
        else {
            throw new ODataSystemException("Unsupported query operation: " + queryOperation);
        }

        return () -> QueryResult.from(categories);
        /*
        {
            return categories;
        };
        */
    }
}
