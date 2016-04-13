'use strict';

describe('Controller Tests', function() {

    describe('RKolektibilitas Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRKolektibilitas;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRKolektibilitas = jasmine.createSpy('MockRKolektibilitas');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'RKolektibilitas': MockRKolektibilitas
            };
            createController = function() {
                $injector.get('$controller')("RKolektibilitasDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'aplikasiApp:rKolektibilitasUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
