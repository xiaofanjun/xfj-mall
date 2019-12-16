# xfj-mall
一套电商产品（包含PC端+后端+手机端（公众号+小程序））
# 代码架构
前后端分离

从大方向分为前端、Web层、Service层

web层：遵循的规则： 请求参数 如果不需要request，则不写，对于多个参数类型的，
使用service层的***Request ，尽量指明参数；Resopse是code+msg+result


service层：请求参数： ***Request（以前的VO），相应数据： code+msg+该业务需要的字段   
  