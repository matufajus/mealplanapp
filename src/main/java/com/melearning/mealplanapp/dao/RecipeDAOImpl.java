//package com.melearning.mealplanapp.dao;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.melearning.mealplanapp.entity.Recipe;
//
//@Repository
//public class RecipeDAOImpl implements RecipeDAO {
//	
//	private EntityManager entityManager;
//	
//	public RecipeDAOImpl(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
//
//	@Override
//	public List<Recipe> findAll() {
//		
//		Session currentSession = entityManager.unwrap(Session.class);
//		
//		Query<Recipe> theQuery = 
//				currentSession.createQuery("from Recipe", Recipe.class);
//		
//		List<Recipe> recipes = theQuery.getResultList();
//		
//		return recipes;
//	}
//
//	@Override
//	public Recipe findById(int id) {
//		Session currentSession = entityManager.unwrap(Session.class);
//		
//		Recipe recipe = currentSession.get(Recipe.class, id);
//		
//		return recipe;
//	}
//
//	@Override
//	public void save(Recipe recipe) {
//		Session currentSession = entityManager.unwrap(Session.class);
//		
//		currentSession.saveOrUpdate(recipe);
//	}
//
//	@Override
//	public void deleteById(int id) {
//		Session currentSession = entityManager.unwrap(Session.class);
//		
//		Query query = currentSession.createQuery(
//				"delete from Recipe where id=:recipeId");
//		
//		query.setParameter("recipeId", id);
//		
//		query.executeUpdate();
//		
//	}
//
//}
