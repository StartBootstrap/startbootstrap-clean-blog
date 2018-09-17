############################################
# TODO auslagern in separates file!!!      #
############################################

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

page.headerData >
page.headerData = COA
page.headerData.10 = TEXT
page.headerData.10.field = title
page.headerData.10.wrap = <title>SC Rhenania Hinsbeck 1919 e.V. - |</title>

# Main page template begin - using fluidtemplating 
page = PAGE
page {
    typeNum = 0
    #insertClassesFromRTE = 1
    10 = FLUIDTEMPLATE
    10 {
        file = fileadmin/templates/rhenania2018/maintemplate.html
        variables {
           contentNormal < styles.content.get        
        }
        partialRootPath     = fileadmin/templates/rhenania2018/partial/
        layoutRootPath      = fileadmin/templates/rhenania2018/layout/          
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

page.meta.description.data = page:description
page.meta.viewport  = width=device-width, initial-scale=1, shrink-to-fit=no
