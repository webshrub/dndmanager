/* Get the template from remote url, Also caches the template for further reuse. */
function getTemplate(templateName, json) {
    if (!ich.templates[templateName]) {
        $.ajax({
            url:"templates/" + templateName + ".tpl",
            dataType:"html",
            async:false,
            success:function (template) {
                ich.addTemplate(templateName, template);
            }
        });
    }
    return ich[templateName](json, true);
}