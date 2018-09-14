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

lib.subtitle = TEXT
lib.subtitle.1 = subtitle


# dynamisch page image background auslesen
lib.pageBgImg = COA
lib.pageBgImg {
  10 = IMG_RESOURCE
  10.file {
    import {
      cObject = TEXT
      cObject.override {
        required = 1
 # slide sets image for all the subpages as well,
 # overwritten if subpage has it's own image added
        data = levelmedia: -1, slide
        wrap = uploads/media/|
 # gets first image from recources list
        listNum = 0
      }
    }
 # image manipulation – if 'c' crop is in action
 # images must be bigger than given width and height
    width = 1400c
    height = 540c
    format = jpg
    quality = 100
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
                linkWrap = <i class="fa fa-universal-access" aria-hidden="true" style="color:#fff"></i> |
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
                wrapItemAndSub = <div class="dropdown-header">|</div><div class="dropdown-divider"></div>
            }
  
}



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
