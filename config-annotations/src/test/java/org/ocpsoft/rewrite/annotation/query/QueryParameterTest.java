package org.ocpsoft.rewrite.annotation.query;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.http.client.methods.HttpGet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ocpsoft.rewrite.annotation.RewriteAnnotationTest;
import org.ocpsoft.rewrite.test.HttpAction;
import org.ocpsoft.rewrite.test.RewriteTest;
import org.ocpsoft.rewrite.test.RewriteTestBase;

@RunWith(Arquillian.class)
public class QueryParameterTest extends RewriteTestBase
{

   @Deployment(testable = false)
   public static WebArchive getDeployment()
   {
      return RewriteTest.getDeploymentWithCDI()
               .addAsLibrary(RewriteAnnotationTest.getRewriteAnnotationArchive())
               .addAsLibrary(RewriteAnnotationTest.getRewriteCdiArchive())
               .addClass(QueryParameterBean.class)
               .addAsWebResource(new StringAsset(
                        "Log: [${queryParameterBean.value}]"),
                        "query.jsp");
   }

   @Test
   public void testQueryParameter() throws Exception
   {
      HttpAction<HttpGet> action = get("/query?q=foo");
      assertEquals(200, action.getStatusCode());
      assertTrue(action.getResponseContent().contains("Log: [foo]"));
   }

}
