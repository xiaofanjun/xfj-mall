<template>
  <div>
    <section class="w mt30 clearfix">
      <y-shelf title="捐赠名单">
        <div slot="content" class="table" v-loading="loading" element-loading-text="加载中...">
          <p>佛祖保佑这些好心人写程序永无BUG，工资翻倍，长命百岁，迎娶白富美，走上人生巅峰！</p>
          <el-table border :data="tableData" :default-sort="{prop: 'time', order: 'descending'}" stripe
                    style="width: 90%">
            <el-table-column sortable prop="nickName" label="昵称" align="center"></el-table-column>
            <el-table-column sortable prop="payType" label="捐赠方式" align="center"></el-table-column>
            <el-table-column sortable prop="money" label="捐赠金额(￥)" align="center"></el-table-column>
            <el-table-column sortable prop="info" label="捐赠人留言信息" align="center"></el-table-column>
            <el-table-column sortable prop="time" label="捐赠时间" align="center"></el-table-column>
          </el-table>

          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 50]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </y-shelf>
    </section>

    <section class="w mt30 clearfix">
      <y-shelf title="为什么要捐赠">
        <div slot="content" class="donate">
          <p>捐赠是国外用来支持开发者和资源贡献者的一种常见的方式。</p>
          <p>这些开发者不通过加入广告或者进行第三方推广获得收入，</p>
          <p>仅通过使用的用户自己主动捐赠来表达对开发者的感谢！</p>
          <p>当一个非盈利项目仅仅依靠兴趣的支撑，很难确定它还能走多远。</p>
          <p>所有的捐赠都将用于提升我的环境配置，维持网站的运行和提高我的积极性。</p>
          <p>这个渠道的存在并不意味着你必须捐赠。</p>
          <p>你也可以不做任何事。你的捐赠意味着你对我过去所做的表示感谢，而不是表达对未来的期望。</p>
          <p>但你的捐赠会提高我的积极性和设备配置让我努力把手头上的事做的更好。</p>
          <p>我会维护一份名单以感谢所有的捐赠者。正如我所说，捐赠是一个向我表示感谢的方式。</p>
        </div>
      </y-shelf>
    </section>

    <section class="w mt30 clearfix">
      <y-shelf :title="thankPanel.name">
        <div slot="content" class="hot">
          <mall-goods :msg="item" v-for="(item,i) in thankPanel.panelContents" :key="i"></mall-goods>
        </div>
      </y-shelf>
    </section>

    <div id="comment" style="width: 1220px;margin: 0 auto;"></div>
  </div>
</template>
<script src="../../js/thanks/thanks.js"></script>
<style src="../../css/thanks/thanks.scss" lang="scss" rel="stylesheet/scss" scoped></style>
