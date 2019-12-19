<template>
  <div class="popup" v-if="open">
    <div class="mask">
      <div class="content">
        <div class="topbar">
          <div class="dialog-tit">
            <h4>{{title}}</h4>
          </div>
          <span class="close" @click="close">
                        <svg t="1501234940517" class="icon" style="" viewBox="0 0 1024 1024" version="1.1"
                             xmlns="http://www.w3.org/2000/svg" p-id="3014" xmlns:xlink="http://www.w3.org/1999/xlink"
                             width="20" height="20"><path
                          d="M941.576 184.248l-101.824-101.824L512 410.176 184.248 82.424 82.424 184.248 410.168 512l-327.744 327.752 101.824 101.824L512 613.824l327.752 327.752 101.824-101.824L613.832 512z"
                          fill="#cdcdcd" p-id="3015"></path></svg>
                    </span>
        </div>
        <div class="s-content">
          <slot name="content"></slot>
        </div>

      </div>
    </div>
  </div>
</template>
<script src="../js/components/popup.js"></script>
<style src="../css/components/popup.scss" lang="scss" scoped></style>

