# override class for rte table - scheint nicht zu funktionieren... 
lib.parseFunc_RTE {
    externalBlocks {
        table.stdWrap.HTMLparser.tags.table.fixAttrib.class {
            default = table-striped table-hover table-bordered
            always = 1
            list >
        }
    }
}


# copyright automatically received via php function
lib.copyright = COA
lib.copyright {
 10 = TEXT
 10.data = date:U
 10.strftime = %Y
}

# read image url from media resource of page
# go upwards in tree if no file found    
lib.headerimage = FILES
lib.headerimage {
    references {
        table = pages
        data = levelmedia: -1, slide
        fieldName = media
    }
    #listNum = rand
    begin = 0
    maxItems = 1
    renderObj = TEXT
    renderObj {
        data = file:current:publicUrl
        wrap = background-image:url(/|);
    }
}
     #     <nav aria-label="breadcrumb">
     #       <ol class="breadcrumb">
     #         <li class="breadcrumb-item"><a href="#">Home</a></li>
     #         <li class="breadcrumb-item"><a href="#">Library</a></li>
     #         <li class="breadcrumb-item active" aria-current="page">Data</li>
     #       </ol>
     #     </nav>

# breadcrump menu
lib.breadcrump = HMENU
lib.breadcrump.special = rootline
lib.breadcrump.special.range = 0|4  
lib.breadcrump {
    special = rootline
    special.range = 0|4
    entryLevel = 0
    wrap =  <nav aria-label="breadcrumb"><ol class="breadcrumb">|</ol></nav>
    1 = TMENU
    1.NO.allWrap =  <li class="breadcrumb-item"><i class="fa fa-chevron-right" aria-hidden="true" style="color: #ccc;"></i>|</li>
    1.NO.ATagTitle.field = abstract // description // title
    1.NO.allWrap =  <li class="breadcrumb-item active" aria-current="page"><i class="fa fa-chevron-right" aria-hidden="true" style="color: #ccc;"></i>|</li>
    1.NO.ATagTitle.field = abstract // description // title

}

# new navigation 
lib.newnav = HMENU
#lib.newnav.wrap = <ul class="vlist"> | </ul>
  # First level menu-object, textual
lib.newnav.1 = TMENU
lib.newnav.1 {
  expAll = 1
 
            NO = 1
            NO {
                ATagTitle.field = title
                ATagParams = class="nav-link" role="button"
                wrapItemAndSub = <li class="nav-item">|</li>
            }
 
            CUR < .NO
            CUR {
                wrapItemAndSub = <li class="nav-item active">|</li>
            }
 
            ACT < .CUR
 
            IFSUB = 1
            IFSUB {
                ATagTitle.field = title
                ATagParams = class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"
                linkWrap = <i class="fa fa-arrow-circle-down" aria-hidden="true" style="color:#fff"></i> |
                ATagBeforeWrap = 1
                wrapItemAndSub = <li class="nav-item dropdown">|</li>
            }
 
            ACTIFSUB < .IFSUB
            ACTIFSUB {
                wrapItemAndSub = <li class="nav-item dropdown active">|</li>
            }
 
            CURIFSUB < .ACTIFSUB
}
  # Second level menu-object, textual
lib.newnav.2 < lib.newnav.1
lib.newnav.2 {
            wrap = <div class="dropdown-menu">|</div>
            NO = 1
            NO {
                ATagTitle.field = title
                ATagParams = class="dropdown-item"
                wrapItemAndSub = |
            }
 
            CUR < .NO
            CUR {
                wrapItemAndSub = |
            }
 
            ACT < .CUR
            // because default Bootstrap doesn't support more submenu levels:
            IFSUB >
            ACTIFSUB >
            CURIFSUB >
 
            SPC = 1
            SPC {
                // no divider, if first menu item on this level (using optionSplit):
                #wrapItemAndSub = <div class="dropdown-header">|</div> |*| <div class="divider"></div><div class="dropdown-header">|</div>
                #wrapItemAndSub = <div class="dropdown-header">|</div><div class="dropdown-divider"></div>
                wrapItemAndSub = <div class="dropdown-header">|</div>
            }
  
}

# Create the navigation structure for the third level, currently only 3rd level is supported. 
# <div class="btn-group">
#        <button type="button" class="btn btn-primary">Apple</button>
#        <button type="button" class="btn btn-primary">Samsung</button>
#        <div class="btn-group">
#          <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
#             Sony
#          </button>
#          <div class="dropdown-menu">
#            <a class="dropdown-item" href="#">Tablet</a>
#            <a class="dropdown-item" href="#">Smartphone</a>
#          </div>
#        </div>
#      </div>


#<nav class="nav nav-pills nav-fill">
 # <a class="btn btn-primary btn-sm nav-item nav-link mr-1 mb-1 active" href="#">Active</a>
 # <a class="btn btn-primary btn-sm nav-item nav-link mr-1 mb-1" href="#">Link</a>
 # <a class="btn btn-primary btn-sm nav-item nav-link mr-1 mb-1" href="#">Link</a>
 # <a class=" btn btn-primary btn-sm nav-item nav-link mr-1 mb-1 disabled" href="#">Disabled</a>
#</nav>


lib.subnav = HMENU
  # First level menu-object, textual
  # start in the second level
lib.subnav.entryLevel = 2 
lib.subnav.wrap = <nav class="nav nav-pills nav-fill">|</nav>
lib.subnav.1 = TMENU
lib.subnav.1 {
  expAll = 1
 
            NO = 1
            NO {
                ATagTitle.field = title
                ATagParams = class="btn btn-outline-dark btn-sm nav-item nav-link mr-1 mb-1"
                stdWrap.wrap = <i class="fa fa-arrow-circle-right" aria-hidden="true" style="color:#fff"></i>&nbsp;|
                #wrapItemAndSub = <button type="button" class="">|</button>
            }
 
            CUR < .NO
            CUR {
                ATagParams = class="btn btn-outline-dark btn-sm nav-item nav-link active mr-1 mb-1"
                stdWrap.wrap = <i class="fa fa-arrow-circle-down" aria-hidden="true" style="color:#fff"></i>&nbsp;|
                #wrapItemAndSub = <button type="button" class="btn btn-primary active">|</button>
            }
 
            ACT < .CUR
}

# configs
config.noPageTitle = 1

# Main page template begin - using fluidtemplating 
page = PAGE
page {
    typeNum = 0
    #insertClassesFromRTE = 1
    10 = FLUIDTEMPLATE
    10 {
        file = fileadmin/templates/rhenania2018/layouts/mainlayout.html
        partialRootPath     = fileadmin/templates/rhenania2018/partials/
        layoutRootPath      = fileadmin/templates/rhenania2018/layouts/    
        variables {
            contentNormal < styles.content.get
            contentNormal.select.where = colPos = 0
            contentLeft < styles.content.get
            contentLeft.select.where = colPos = 1
            contentRight < styles.content.get
            contentRight.select.where = colPos = 2      
         }      
    }  
    
    includeCSS {
        bootstrap =  fileadmin/templates/rhenania2018/vendor/bootstrap/css/bootstrap.min.css
        bootstrap.forceOnTop = 1
        fontawesome = fileadmin/templates/rhenania2018/vendor/font-awesome/css/font-awesome.min.css
        lorafont = https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic
        opensansfont = https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800
        sitestyle = fileadmin/templates/rhenania2018/css/clean-blog.min.css
        sitestyle.media = screen   
    }
      
    #includeJSlibs {
    #    modernizr           = fileadmin/template/rhenania2015_foundation/js/vendor/modernizr.js
    #    modernizr.forceOnTop= 1
    #}

    includeJSFooterlibs  {
         jquery = fileadmin/templates/rhenania2018/vendor/jquery/jquery.min.js
         jquery.forceOnTop = 1 
         bootstrap = fileadmin/templates/rhenania2018/vendor/bootstrap/js/bootstrap.bundle.min.js
         customjs = fileadmin/templates/rhenania2018/js/clean-blog.min.js
    }
}

# map the backend layout to the template
page.10.file.stdWrap.cObject = CASE
page.10.file.stdWrap.cObject {
    key.data = levelfield:-1, backend_layout_next_level, slide
    key.override.field = backend_layout
    
    default = TEXT
    default.value = fileadmin/templates/rhenania2018/maintemplate.html
    2 = TEXT
    2.value = fileadmin/templates/rhenania2018/maintemplate.html
    3 = TEXT
    3.value = fileadmin/templates/rhenania2018/maintemplate_2_cols.html
    4 = TEXT
    4.value = fileadmin/templates/rhenania2018/maintemplate_3_cols.html
}


page.meta.description.data = page:description
page.meta.viewport  = width=device-width, initial-scale=1, shrink-to-fit=no
page.headerData >
page.headerData = COA
page.headerData.10 = TEXT
page.headerData.10.field = title
page.headerData.10.wrap = <title>SC Rhenania Hinsbeck 1919 e.V. - |</title>
page.headerData.20 = TEXT
page.headerData.20.value = <link rel="apple-touch-icon" href="fileadmin/templates/rhenania2018/img/apple-touch-icon.png"/>

# add class to images in tt content
# Remove some of the div wraps
tt_content.image.20.imageStdWrap.dataWrap >
tt_content.image.20.imageStdWrapNoWidth.dataWrap >
tt_content.image.20.imageColumnStdWrap.dataWrap >

# Redefine the layout switch with only one default case
tt_content.image.20.layout >
tt_content.image.20.layout = CASE
tt_content.image.20.layout.key.field = imageorient
tt_content.image.20.layout.default = TEXT
tt_content.image.20.layout.default.value = ###IMAGES### ###TEXT###

# Remove the wrap around the image subtext
tt_content.textpic.20.text.wrap = |

# Define a new rendering method without wraps
tt_content.image.20.rendering.noWraps {
   imageRowStdWrap.dataWrap = |
   noRowsStdWrap.wrap =
   oneImageStdWrap.dataWrap = |
   imgTagStdWrap.wrap = |
   editIconsStdWrap.wrap = |
   caption.wrap = |
}

# Set this as active rendering method
tt_content.image.20.renderMethod = noWraps

#add custom class to image
tt_content.image.20.1.params.cObject = CASE
tt_content.image.20.1.params.cObject {
  key.field = layout
  default = TEXT
  default.value = class="img-fluid img-thumbnail image-responsive"
  1 = TEXT
  1.value = class="class2"
  2 = TEXT
  2.value = class="img-responsive"
}
