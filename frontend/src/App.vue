<template>
  <div id="app">
    <MainMenu/>
    <SideBar class="side-bar"/>
  </div>
</template>

<script>
import MainMenu from "@/components/MainMenu";
import SideBar from "@/components/SiderBar";

export default {
  name: 'App',
  components: {
    SideBar,
    MainMenu
  }
}
</script>

<style>
#app {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
}

.side-bar {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
}

</style>
