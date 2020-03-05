
function AnalyticsPlugin() {}


AnalyticsPlugin.prototype.setUserProfile = function(arg0, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, 'AnalyticsPlugin', 'setUserProfile', [arg0]);
}

AnalyticsPlugin.prototype.logEvent = function(arg0, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, 'AnalyticsPlugin', 'logEvent', [arg0]);
}

// Installation constructor that binds AnalyticsPlugin to window
AnalyticsPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.AnalyticsPlugin = new AnalyticsPlugin();
  return window.plugins.AnalyticsPlugin;
};
cordova.addConstructor(AnalyticsPlugin.install);